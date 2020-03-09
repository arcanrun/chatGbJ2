package lesson06;

import java.util.ArrayList;



public class HomeWork {
    public int[] newArr(int[] arr) {
        if (arr.length == 0) throw new RuntimeException();
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int i : arr) {
            arrayList.add(i);
        }
        int lastEntrance = arrayList.lastIndexOf(4);
        if (lastEntrance == -1) throw new RuntimeException();
        int[] res = new int[arrayList.size() - lastEntrance - 1];

        for (int i = 0; i < res.length; i++) {

            res[i] = arrayList.get(i + lastEntrance + 1);
        }
        return res;
    }

    public boolean checkArr(int[] arr) {
        ArrayList<Integer> newArr = new ArrayList<>();
        for (int i : arr) {
            newArr.add(i);
        }
        return newArr.contains(4) | newArr.contains(1);
    }
}
