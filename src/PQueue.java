import java.util.Map;
import java.util.HashMap;

public class PQueue<T extends Queable> {
	private int used, alloc;
	// underlying array for array-based binary heap
	private T[] ar;
	private Map<Integer, Integer> position;

	@SuppressWarnings("unchecked")
	public PQueue() {
		used = 0;
		alloc = 4;

		ar = (T[]) new Queable[1 + 4];
		position = new HashMap<Integer, Integer>();
	}

	@SuppressWarnings("unchecked")
	private void resize(int newSize) {
		T[] oldAr = ar;
		ar = (T[]) new Queable[1 + newSize];
		for (int i = 1; i < used + 1; i++)
			ar[i] = oldAr[i];
		alloc = newSize;
	}

	public boolean empty() {
		return used == 0;
	}

	// not used, but useful
	public int size() {
		return used;
	}

	public boolean contains(int elemId) {
		return position.get(elemId) != null;
	}

	private void swap(int pos1, int pos2) {
		T tmp = ar[pos1];
		ar[pos1] = ar[pos2];
		ar[pos2] = tmp;

		position.put(ar[pos1].id(), pos1);
		position.put(ar[pos2].id(), pos2);
	}

	private void down(int pos) {
		int child;

		for (; pos * 2 <= used; pos = child) {
			child = pos * 2;
			if (child + 1 <= used && ar[child + 1].priority() < ar[child].priority())
				child++;
			if (ar[child].priority() < ar[pos].priority()) {
				swap(pos, child);
			} else
				break;
		}
	}

	private void up(int pos) {
		int old = pos;
		pos /= 2;
		// TODO: old priority can be saved throughout iterations
		while (pos > 0 && ar[pos].priority() > ar[old].priority()) {
			swap(old, pos);

			old = pos;
			pos /= 2;
		}
	}

	public void insert(T elem) {
		if (used >= alloc)
			resize(2 * alloc);
		used++;

		ar[used] = elem;
		position.put(elem.id(), used);

		up(used);
	}

	public T min() throws EmptyQueueException {
		if (used < 1)
			throw new EmptyQueueException();
		return ar[1];
	}

	public T extractMin() throws EmptyQueueException {
		if (used < 1)
			throw new EmptyQueueException();
		if (used < alloc / 2)
			resize(alloc / 2);

		T ret = ar[1];
		ar[1] = ar[used];
		used--;

		position.put(ar[1].id(), 1);
		position.remove(ret.id());

		down(1);
		return ret;
	}

	public boolean decreasePriorityAndContainsTest(int elemId, double newP) {
		Integer elemPos_ob = position.get(elemId);
		if (elemPos_ob == null)
			return false;
		int pos = elemPos_ob.intValue();

		double oldP = ar[pos].priority();
		if (newP < oldP) {
			ar[pos].setPriority(newP);
			up(pos);
		}

		return true;
	}

	public boolean changePriority(int elemId, double newPriority) {
		Integer elemPos_ob = position.get(elemId);
		if (elemPos_ob == null)
			return false;
		int elemPos = elemPos_ob.intValue();

		double oldPriority = ar[elemPos].priority();
		ar[elemPos].setPriority(newPriority);

		if (oldPriority < newPriority)
			down(elemPos);
		else
			up(elemPos);

		return true;
	}

	public T remove(int elemId) throws EmptyQueueException {
		if (changePriority(elemId, Integer.MIN_VALUE))
			return extractMin();
		return null;
	}

	public double priority(int elemId) {
		Integer elemPos_ob = position.get(elemId);
		if (elemPos_ob == null)
			return -1;
		return ar[elemPos_ob.intValue()].priority();
	}

	/**
	 * Thrown when trying to get element from an empty queue.
	 */
	@SuppressWarnings("serial")
	public static class EmptyQueueException extends Exception {
		/**
		 * Constructs an EmptyQueueException with null as its error message
		 * string.
		 */
		public EmptyQueueException() {
			super();
		}
	}
}