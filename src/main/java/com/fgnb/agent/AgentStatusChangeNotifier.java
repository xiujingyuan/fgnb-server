package com.fgnb.agent;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by jiangyitao.
 */
@Component
@Slf4j
public class AgentStatusChangeNotifier extends AbstractStatusChangeNotifier {
    public AgentStatusChangeNotifier(InstanceRepository repositpry) {
        super(repositpry);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
                if(!StatusInfo.STATUS_UP.equals(status)){
                    //非上线
                    String agentURL = instance.getRegistration().getServiceUrl();//http://xx.xx.xx.xx:xxx/
                    //todo 将该agent上的所有手机 下线
                }
            }
        });
    }
}
