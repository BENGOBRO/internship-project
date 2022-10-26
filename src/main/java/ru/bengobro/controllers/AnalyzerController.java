package ru.bengobro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengobro.services.AnalyzerService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analyzer")
@AllArgsConstructor
@Tag(name = "Analyzer", description = "Methods for some statistics about a set of numbers")
public class AnalyzerController {

    private final AnalyzerService analyzerService;

    @GetMapping(value = "/get_max_value",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Get the maximum value from a set of numbers")
    public Map<String, Long> getMaxValue() {
        return Collections.singletonMap("max_value", analyzerService.getMaxValue());
    }

    @GetMapping(value = "/get_min_value",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Get the minimum value from a set of numbers")
    public Map<String, Long> getMinValue() {
        return Collections.singletonMap("min_value", analyzerService.getMinValue());
    }

    @GetMapping(value = "/get_median",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Get the median value from a set of numbers")
    public Map<String, Double> getMedian() {
        return Collections.singletonMap("median", analyzerService.getMedian());
    }

    @GetMapping(value = "/get_average",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Get the average from a set of numbers")
    public Map<String, Double> getAverage() {
        return Collections.singletonMap("average", analyzerService.getAverage());
    }

    @GetMapping(value = "/get_max_ascending_sequences",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Get max ascending sequences from a set of numbers")
    public Map<String, List<List<Long>>> getMaxAscendingSequences() {
        return Collections.singletonMap("max_ascending_sequences",
                analyzerService.getMaxAscendingOrDescendingSequences(true));
    }

    @GetMapping(value = "/get_max_descending_sequences",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Get max descending sequences from a set of numbers")
    public Map<String, List<List<Long>>> getMaxDescendingSequences() {
        return Collections.singletonMap("max_descending_sequences",
                analyzerService.getMaxAscendingOrDescendingSequences(false));
    }

    @PostMapping("/read_file")
    @Operation(summary = "Read file from source")
    public ResponseEntity<HttpStatus> readFile(@Parameter(description = "File path")
                                                   @RequestBody Map<String, String> file_path) {
        analyzerService.evict();
        return analyzerService.readFile(file_path.get("file_path")) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
