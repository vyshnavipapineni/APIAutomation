package objects;

import middleWare.CreateUser;
import middleWare.GetAllProducts;
import middleWare.GetAllUsers;
import middleWare.SearchProducts;

public class ManageApiObjects {



    private GetAllUsers getAllUsers;
    private CreateUser createUser;

    private SearchProducts searchProducts;
private GetAllProducts getAllProducts;
    public GetAllUsers getAllUsers(){

        return (getAllUsers==null) ?getAllUsers= new GetAllUsers():getAllUsers;
    }

    public CreateUser createUser(){
        return createUser==null ? createUser= new CreateUser():createUser;
    }


    public GetAllProducts getAllProducts(){
        return getAllProducts==null ? getAllProducts= new GetAllProducts():getAllProducts;
    }

    public SearchProducts searchProducts(){

        return  searchProducts==null? searchProducts=new SearchProducts():searchProducts;
    }


}
