package gov.hhs.onc.sdcct.beans;

import com.google.common.collect.ListMultimap;
import gov.hhs.onc.sdcct.api.SdcctIssueSeverity;
import java.util.List;

public interface ResultBean {
    public boolean hasMessages(SdcctIssueSeverity severity);

    public boolean hasMessages();

    public List<MessageBean> getMessages(SdcctIssueSeverity severity);

    public ListMultimap<SdcctIssueSeverity, MessageBean> getMessages();

    public boolean isSuccess();
}
