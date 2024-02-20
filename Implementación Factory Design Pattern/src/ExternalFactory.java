public class ExternalFactory implements UserFactory{
    public IUser createUser(){
        return new External();
    }
}
