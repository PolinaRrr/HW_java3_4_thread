public class Main {

    static final Object monitor = new Object();
    static volatile char letter = 'A';

    static class PrintLetter implements Runnable {
        private char actLetter;
        private char secLetter;

        public PrintLetter(char actLetter, char secLetter) {
            this.actLetter = actLetter;
            this.secLetter = secLetter;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (monitor) {
                        while (letter != actLetter) {
                            monitor.wait();
                        }
                        System.out.println(actLetter);
                        letter = secLetter;
                        monitor.notifyAll();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new PrintLetter('A', 'B')).start();
        new Thread(new PrintLetter('B', 'C')).start();
        new Thread(new PrintLetter('C', 'A')).start();

    }
}
