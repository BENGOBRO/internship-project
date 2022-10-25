package ru.bengobro.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AnalyzerService {

    private List<Long> numbers;

    //@Cacheable("file")
    public boolean readFile(String filePath) {
        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            numbers = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                numbers.add(Long.valueOf(line));
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private LongSummaryStatistics getStatistics() {
        return numbers.stream()
                .mapToLong(Long::longValue)
                .summaryStatistics();
    }

    @Cacheable("max")
    public Long getMaxValue() {
        return getStatistics().getMax();
    }

    @Cacheable("min")
    public Long getMinValue() {
        return getStatistics().getMin();
    }

    @Cacheable("average")
    public Double getAverage() {
        return getStatistics().getAverage();
    }

    @Cacheable("median")
    public Double getMedian() {
        List<Long> copyOfNumbers = numbers;
        Collections.sort(copyOfNumbers);
        if (copyOfNumbers.size() % 2 != 0) {
            return Double.valueOf(copyOfNumbers.get(copyOfNumbers.size() / 2));
        } else {
            return (double) ((copyOfNumbers.get(copyOfNumbers.size() / 2 - 1)
                    + copyOfNumbers.get(copyOfNumbers.size() / 2)) / 2);
        }
    }

    @Cacheable("ascending_sequences")
    public List<List<Long>> getMaxAscendingSequences() {
        List<List<Long>> result = new ArrayList<>();
        List<Long> currentList = new ArrayList<>();
        boolean newSeq = true;
        int size = 0;
        int max = 0;
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) <= numbers.get(i - 1)) {
                result.add(currentList);
                newSeq = true;
                max = Math.max(size, max);
                size = 0;
                currentList = new ArrayList<>();
            } else if (numbers.get(i) > numbers.get(i - 1) && newSeq) {
                currentList.add(numbers.get(i - 1));
                currentList.add(numbers.get(i));
                size += 2;
                newSeq = false;
                if (i == numbers.size() - 1) {
                    result.add(currentList);
                }
            } else if (numbers.get(i) > numbers.get(i - 1) && !newSeq) {
                currentList.add(numbers.get(i));
                size++;
                if (i == numbers.size() - 1) {
                    result.add(currentList);
                }
            }
        }

        return getFinalResult(result, max);
    }

    @Cacheable("descending_sequences")
    public List<List<Long>> getMaxDescendingSequences() {
        List<List<Long>> result = new ArrayList<>();
        List<Long> currentList = new ArrayList<>();
        boolean newSeq = true;
        int size = 0;
        int max = 0;
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) >= numbers.get(i - 1)) {
                result.add(currentList);
                newSeq = true;
                max = Math.max(size, max);
                size = 0;
                currentList = new ArrayList<>();
            } else if (numbers.get(i) < numbers.get(i - 1) && newSeq) {
                currentList.add(numbers.get(i - 1));
                currentList.add(numbers.get(i));
                size += 2;
                newSeq = false;
                if (i == numbers.size() - 1) {
                    result.add(currentList);
                }
            } else if (numbers.get(i) < numbers.get(i - 1) && !newSeq) {
                currentList.add(numbers.get(i));
                size++;
                if (i == numbers.size() - 1) {
                    result.add(currentList);
                }
            }
        }

        return getFinalResult(result, max);
    }

    private List<List<Long>> getFinalResult(List<List<Long>> result, int max) {
        List<List<Long>> finalResult = new ArrayList<>();
        for (List<Long> longs : result) {
            if (longs.size() == max) {
                finalResult.add(longs);
            }
        }
        return finalResult;
    }

    @Caching(evict = {
            @CacheEvict("max"),
            @CacheEvict("min"),
            @CacheEvict("average"),
            @CacheEvict("median"),
            @CacheEvict("ascending_sequences"),
            @CacheEvict("descending_sequences"),
            @CacheEvict("file")
    })
    public void evict() {}
}
