package definition.value.random.impl.string;


import definition.value.random.api.RandomValueCreator;
import java.util.stream.Collectors;

public class StringRand extends RandomValueCreator<String> {

    public StringRand() {
        super(null, null);
    }

    @Override
    public String createValue() {

        final String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ().-_,?!";
        int timesToRepeat = 50;
        StringBuilder randomStringBuilder = new StringBuilder();

        return random.ints(timesToRepeat, 0, charSet.length())
                .mapToObj(charSet::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }


}
