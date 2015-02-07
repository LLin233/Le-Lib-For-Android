public interface FixedSizeBlockingQueue<E> {

      // only initialize this queue once and throws Exception if the user is trying to initialize it multiple t times.
        public void init(int capacity) throws Exception;

      // throws Exception if the queue is not initialized
        public void push(E obj) throws Exception;

      // throws Exception if the queue is not initialized
        public E pop() throws Exception;

      // implement an atomic putList function which can put a list of object atomically. By atomically it mean the objs in the list should be next to each other in the queue. The size of the list could be larger than the queue capacity.
      // throws Exception if the queue is not initialized
        public void pushList(List<E> objs) throws Exception;
}
