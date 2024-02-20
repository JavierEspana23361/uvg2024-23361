public class StudentFactory implements UserFactory{
    public IUser createUser(){
        return new Student();
    }
}
