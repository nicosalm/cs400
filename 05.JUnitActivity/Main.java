public class Main {

    public static void main(String[] args) {
        MyList<String> list = new MyList<String>();
        list.add("Chef Boyardee");
        list.add("SpaghettiOs");
        list.add("Fine Italian red wine");

        list.remove(0);
        list.get(0);
        list.size();

        System.out.println(list);
    }

}
