import java.util.*;

public class Main {

    public static final Map<Integer,Integer> SIZE_TO_FREQ = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        String[] routs = new String[1000];

        for (int i = 0; i < routs.length; i++) {
            routs[i] = generateRoute("RLRFR", 100);
        }

        List<Thread> threads = new ArrayList<>();

        for (String rout : routs) {
            Runnable logic = () -> {
                Integer amountR = 0;
                for (int i = 0; i < rout.length(); i++) {
                    if (rout.charAt(i) == 'R') {
                        amountR++;
                    }
                }
                System.out.println(rout.substring(0,100) + " -> " + amountR);

                synchronized (amountR) {
                    if(SIZE_TO_FREQ.containsKey(amountR)) {
                        SIZE_TO_FREQ.put(amountR, SIZE_TO_FREQ.get(amountR) + 1);
                    }
                    else {
                        SIZE_TO_FREQ.put(amountR, 1);
                    }
                }
            };

            Thread thread = new Thread(logic);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int maxValue = 0;
        Integer maxKey = 0;

        for (Integer key : SIZE_TO_FREQ.keySet()) {
            if(SIZE_TO_FREQ.get(key) > maxValue) {
                maxValue = SIZE_TO_FREQ.get(key);
                maxKey = key;
            }
        }

        System.out.println("Самое частое количество повторений "
                + maxKey + " (встретилось " + maxValue + " раз)");
        SIZE_TO_FREQ.remove(maxKey);

        System.out.println("Другие размеры:");
        for (Integer key : SIZE_TO_FREQ.keySet()) {
            int value = SIZE_TO_FREQ.get(key);
            System.out.println("- " + key + " (" + value + " раз)");
        }
    }

    private static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
