package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static List<String> data = Arrays.asList(
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d");

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int result = doParallelWork(data);
        System.out.println(result);
    }

    public static int doParallelWork(List<String> data) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        int totalChars = 0;
        try {
            // создаем три Callable для подсчета символов в каждом элементе списка
            List<Callable<Integer>> tasks = Arrays.asList(
                    () -> countChars(data.subList(0, 16)),
                    () -> countChars(data.subList(16, 32)),
                    () -> countChars(data.subList(32, 48))
            );

            // запускаем все задачи и получаем список будущих результатов
            List<Future<Integer>> results = executorService.invokeAll(tasks);

            // суммируем результаты
            for (Future<Integer> result : results) {
                totalChars += result.get();
            }
        } finally {
            executorService.shutdown();
        }
        return totalChars;
    }

    private static int countChars(List<String> data) {
        int count = 0;
        for (String s : data) {
            count += s.length();
        }
        return count;
    }
}
