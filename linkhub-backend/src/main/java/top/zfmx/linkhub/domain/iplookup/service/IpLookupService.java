package top.zfmx.linkhub.domain.iplookup.service;

import top.zfmx.linkhub.infrastructure.external.IpLookupServiceImpl;
import top.zfmx.linkhub.interfaces.dto.LookupResult;

/**
 * @author zfmx
 * @version 0.0.1
 **/

public interface IpLookupService {
    LookupResult lookup(String ip) throws IpLookupServiceImpl.InvalidIpException;
}
