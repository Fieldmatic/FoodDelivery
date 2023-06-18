package eryce.bringit.shared.model.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum OrderStatus {

    REJECTED,
    ACCEPTED,
    MAKING_IN_PROGRESS,
    DELIVERY_IN_PROGRESS,
    COMPLETED
}
