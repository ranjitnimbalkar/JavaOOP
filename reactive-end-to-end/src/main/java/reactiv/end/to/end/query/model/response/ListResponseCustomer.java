package reactiv.end.to.end.query.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import reactiv.end.to.end.query.bean.CustomerBean;
import reactiv.end.to.end.query.model.ListResponse;

@Schema(title = "Customers")
public class ListResponseCustomer extends ListResponse<CustomerBean> {
    //
}
