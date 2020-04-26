public class Cat implements Comparable {
    public String nick;
    public String color;
    public int age;

    public Cat(String nick, String color, int age) {
        this.nick = nick;
        this.color = color;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "nick='" + nick + '\'' +
                ", color='" + color + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Cat another = (Cat) o;
        if (this.age > another.age) {
            return 1;
        }
        if (this.age < another.age) {
            return -1;
        }
        return 0;
    }
}