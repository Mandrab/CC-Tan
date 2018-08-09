package it.unibo.oop.cctan.view;


public class Triple<L, M, R> implements java.io.Serializable {

        private static final long serialVersionUID = -8127158209823396900L;
        private final L element0;
        private final M element1;
        private final R element2;

        public Triple(L element0, M element1, R element2) {
                this.element0 = element0;
                this.element1 = element1;
                this.element2 = element2;
        }

        public static <L, M, R> Triple<L, M, R> createTriple(L element0, M element1, R element2) {
                return new Triple<L, M, R>(element0, element1, element2);
        }

        public L getElement0() {
                return element0;
        }

        public M getElement1() {
                return element1;
        }

        public R getElement2() {
                return element2;
        }
}
