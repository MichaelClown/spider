package com.spider.order.ecommerce.service;

import com.spider.common.SpiderBusinessException;
import com.spider.order.Company;
import com.spider.order.ecommerce.domain.*;
import com.spider.order.ecommerce.repository.MallAccessRepository;
import com.spider.order.logistics.domain.LogisticsOrder;
import com.spider.spider.logistics.LogisticsOrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/3/23.
 */
@Service
public class MallAccessService {

    private MallAccessRepository mallAccessRepository;

    private Logger LOGGER = LoggerFactory.getLogger(MallAccessService.class);

    @Transactional
    public void processMessage(MallOrderMessage mallOrderMessage) {

        Integer orderCount = mallAccessRepository.getCountByInnerOrderId(mallOrderMessage.getCompanyId(), mallOrderMessage.getInnerOrderId());

        // 处理电商订单
        if (orderCount == null || orderCount > 0) {
            LOGGER.warn("MallAccessService : already exists record with companyId {} and innerOrderId {}, ignore", mallOrderMessage.getCompanyId(), mallOrderMessage.getInnerOrderId());
            return;
        } else if (mallAccessRepository.addEcommerceOrder(mallOrderMessage) == null) {
            LOGGER.error("MallAccessService : addEcommerceOrder Failed");
            throw new SpiderBusinessException("MallAccessService : addEcommerceOrder Failed");
        }

        // 处理电商订单中的买家信息
        Customer existedBuyer = processActorAccount(mallOrderMessage.getOrderDetail().getCustomer());

        // 处理电商订单中的买家地址信息
        LogisticsOrder logisticsOrder = processBuyerAddress(existedBuyer, mallOrderMessage.getOrderDetail().getAddress());

        // 处理电商订单中的卖家信息
        Customer existedSeller = processActorAccount(mallOrderMessage.getOrderDetail().getSeller());

        logisticsOrder.setStatus(LogisticsOrderStatus.FOR_DELIVERY);
        logisticsOrder.setEcommerce(new Company(mallOrderMessage.getCompanyId()));

        // 生成基础物流订单
        processLogisticsOrder(logisticsOrder);

    }

    //处理用户账户信息
    private Customer processActorAccount(Customer customer) {
        if (customer == null) {
            LOGGER.error("MallAccessService : processActorAccount with null customer");
            throw new SpiderBusinessException("MallAccessService : processActorAccount with null customer, ignore");
        }
        // 处理账号信息
        Customer existedCustomer = mallAccessRepository.lockOnCustomer(customer.getCellPhone());
        if (existedCustomer == null) {
            if (mallAccessRepository.addCustomer(customer) == null) {
                throw new SpiderBusinessException("Failed to add customer");
            } else {
                existedCustomer = mallAccessRepository.lockOnCustomer(customer.getCellPhone());
            }
        }
        return existedCustomer;
    }

    //处理用户地址信息
    private LogisticsOrder processBuyerAddress(Customer customer, Address address) {
        if (address == null) {
            LOGGER.error("MallAccessService : processBuyerAddress with null address");
            throw new SpiderBusinessException("MallAccessService : processBuyerAddress with null address, ignore");
        }
        Actor lockOnActor = mallAccessRepository.lockOnActor(address.getCellPhone(), customer.getCustomerId());
        // 处理收件人信息
        if (lockOnActor == null) {
            if (mallAccessRepository.addActor(buildActorByCustomerAndAddress(customer, address)) == null) {
                throw new SpiderBusinessException("Failed to add actor");
            } else {
                lockOnActor = mallAccessRepository.lockOnActor(address.getCellPhone(), customer.getCustomerId());
            }
        }

        // 处理收件人地址信息
        Address existedAddresss = mallAccessRepository.lockOnAddress(address.getAddressId());
        if (existedAddresss == null) {
            address.setActorId(lockOnActor.getActorId());
            if (mallAccessRepository.addAddress(address) == null) {
                throw new SpiderBusinessException("Filed to add address");
            } else {
                existedAddresss = mallAccessRepository.lockOnAddress(address.getAddressId());
            }
        }

        return buildLogisticsOrder(existedAddresss, lockOnActor);
    }

    private Actor buildActorByCustomerAndAddress(Customer customer, Address address) {
        Actor actor = new Actor();
        actor.setCustomerId(customer.getCustomerId());
        actor.setCellPhone(address.getCellPhone());
        actor.setRealName(address.getName());
        return actor;
    }

    // 构建含有收件人的订单信息
    private LogisticsOrder buildLogisticsOrder(Address address, Actor actor) {
        LogisticsOrder logisticsOrder = new LogisticsOrder();
        logisticsOrder.setDestinationActor(actor);
        logisticsOrder.setDestinationAddress(address);
        return logisticsOrder;
    }

    // 处理物流订单生成待发货订单
    private void processLogisticsOrder(LogisticsOrder logisticsOrder) {
        // 如果是待发货状态，则默认为一个新创建订单
        if (LogisticsOrderStatus.FOR_DELIVERY.name().equals(logisticsOrder.getStatus().name())) {
            if (mallAccessRepository.addLogisticsOrder(logisticsOrder) == null) {
                throw new SpiderBusinessException("Failed to add logisticsOrder");
            }
        }
    }

    @Inject
    public void setMallAccessRepository(MallAccessRepository mallAccessRepository) {
        this.mallAccessRepository = mallAccessRepository;
    }
}
