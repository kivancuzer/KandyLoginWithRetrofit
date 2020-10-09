package com.example.kandyloginwithretrofit;

import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class CPaaSManager {

    private static CPaaSManager instance;
    private CPaaS cpaas;

    private CPaaSManager() {
    }

    /**
     * Get Instance
     *
     * Thread Safe Singleton
     *
     * @return instance of CPaaSManager
     */
    public synchronized static CPaaSManager getInstance() {
        if (instance == null) {
            instance = new CPaaSManager();
        }
        return instance;
    }

    public CPaaS getCpaas() {
        if (cpaas == null) {
            List<ServiceInfo> services = new ArrayList<>();
            services.add(new ServiceInfo(ServiceType.SMS, true));
            services.add(new ServiceInfo(ServiceType.CALL, true));
            services.add(new ServiceInfo(ServiceType.ADDRESSBOOK, true));
            services.add(new ServiceInfo(ServiceType.CHAT, true));
            cpaas = new CPaaS(services);
        }
        return cpaas;
    }

}
