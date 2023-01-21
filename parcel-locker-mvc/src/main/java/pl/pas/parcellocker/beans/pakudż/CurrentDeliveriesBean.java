package pl.pas.parcellocker.beans.pakudż;


import static pl.pas.parcellocker.delivery.http.ModulePaths.DELIVERIES_PATH;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.Conversational;
import pl.pas.parcellocker.delivery.http.ClientHttp;
import pl.pas.parcellocker.model.delivery.Delivery;


@Named
@ViewScoped
@Getter
@Setter
public class CurrentDeliveriesBean extends Conversational implements Serializable {

    @Inject
    ClientHttp moduleExecutor;

    List<Delivery> deliveries;

//    @Inject
//    EditProductController editProductController;

//    @Inject
//    UserApiClient userApiClient;
//    @Getter
//    @Setter
//    String currentUserId;

//    boolean isCreatingNewProduct = true;

    @PostConstruct
    public void initCurrentProducts() {
        moduleExecutor.setPathForRemoteCall(DELIVERIES_PATH);
        deliveries = moduleExecutor.getTarget().request(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<>() {
        });
    }

    public String delete(Delivery delivery) {
        moduleExecutor.setPathForRemoteCall(DELIVERIES_PATH + "/{id}");

        moduleExecutor.getTarget().resolveTemplate("id", delivery.getId())
                .request()
                .delete();

        return "allDeliveries";
    }

//    public String editProduct(Product product) {
//        beginNewConversation();
//        editProductController.setCurrentProduct(product);
//        editProductController.setProductType(ViewUtils.getClassName(product));
//        return "EditProduct";
//    }

//    public String getDetails(Product product) {
//        beginNewConversation();
//        editProductController.setCurrentProduct(product);
//        editProductController.setProductType(ViewUtils.getClassName(product));
//        editProductController.setCurrentUsers(userApiClient.getAllUsers().stream().collect(Collectors.toMap(IdTrait::getId, User::getLogin)));
//        editProductController.setCurrentProductOrders(productApiClient.ordersContainingProduct(product.getId()));
//        return "ProductDetails";
//    }
}
