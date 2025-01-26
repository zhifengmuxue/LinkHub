package top.zfmx.linkhub.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ip 查询响应类
 *
 * @author zfmx
 * @version 0.0.1
 **/

@Data
@AllArgsConstructor
public class LookupResult {
    private String ip;
    private String isp;
    private String asn;
    private String org;
    private String country;
    private String city;
}
