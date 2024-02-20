public class AdminFactory implements UserFactory{
    public IUser createUser(){
        return new Admin();
    }
}
