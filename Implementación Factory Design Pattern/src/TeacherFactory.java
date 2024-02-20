public class TeacherFactory implements UserFactory{
    public IUser createUser(){
        return new Teacher();
    }
}
