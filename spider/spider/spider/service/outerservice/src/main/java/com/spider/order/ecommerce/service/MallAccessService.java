package com.spider.order.ecommerce.service;

import com.spider.order.ecommerce.domain.MallOrderMessage;
import com.spider.order.ecommerce.repository.MallAccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/4/23.
 */
@Service
public class MallAccessService {

    private MallAccessRepository mallAccessRepository;

    private Logger LOGGER = LoggerFactory.getLogger(MallAccessService.class);

    @Transactional
    public void processMessage(MallOrderMessage mallOrderMessage) throws Exception {
        Integer orderCount = mallAccessRepository.getCountByInnerOrderId(mallOrderMessage.getCompanyId(), mallOrderMessage.getInnerOrderId());
        if (orderCount == null || orderCount > 0) {
            LOGGER.warn("MallAccessService : already exists record with companyId {} and innerOrderId {}, ignore", mallOrderMessage.getCompanyId(), mallOrderMessage.getInnerOrderId());
            return;
        } else if (mallAccessRepository.addEcommerceOrder(mallOrderMessage) == null) {
            LOGGER.error("MallAccessService : addEcommerceOrder Failed");
            throw new Exception("MallAccessService : addEcommerceOrder Failed");
        }

    }

    @Inject
    public void setMallAccessRepository(MallAccessRepository mallAccessRepository) {
        this.mallAccessRepository = mallAccessRepository;
    }
}
