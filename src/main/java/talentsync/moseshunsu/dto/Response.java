package talentsync.moseshunsu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class Response {
    private String responseCode;
    private String responseMessage;
}
