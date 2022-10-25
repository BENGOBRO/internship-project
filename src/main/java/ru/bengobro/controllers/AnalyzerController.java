package ru.bengobro.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
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
public class AnalyzerController {

    private final AnalyzerService analyzerService;

    @GetMapping(value = "/get_max_value",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Map<String, Long> getMaxValue() {
        return Collections.singletonMap("max_value", analyzerService.getMaxValue());
    }

    @GetMapping(value = "/get_min_value",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Map<String, Long> getMinValue() {
        return Collections.singletonMap("min_value", analyzerService.getMinValue());
    }

    @GetMapping(value = "/get_median",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Map<String, Double> getMedian() {
        return Collections.singletonMap("median", analyzerService.getMedian());
    }

    @GetMapping(value = "/get_average",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Map<String, Double> getAverage() {
        return Collections.singletonMap("average", analyzerService.getAverage());
    }

    @GetMapping(value = "/get_max_ascending_sequence",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Map<String, List<List<Long>>> getMaxAscendingSequences() {
        return Collections.singletonMap("max_ascending_sequences",
                analyzerService.getMaxAscendingSequences());
    }

    @GetMapping(value = "/get_max_descending_sequence",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Map<String, List<List<Long>>> getMaxDescendingSequences() {
        return Collections.singletonMap("max_descending_sequences",
                analyzerService.getMaxDescendingSequences());
    }

    @PostMapping("/read_file")
    public ResponseEntity<HttpStatus> openFile(@RequestBody Map<String, String> file_path) {
        analyzerService.evict();
        return analyzerService.readFile(file_path.get("file_path")) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/operation")
    public ResponseEntity<Map<String, Long>> getMinOrMax(@RequestBody Map<String, String> operation) {
        if (operation.get("operation").equals("min_value")) {
            return new ResponseEntity<>(Collections.singletonMap("min_value", analyzerService.getMinValue()), HttpStatus.OK);
        } else if (operation.get("operation").equals("max_value")) {
            return new ResponseEntity<>(Collections.singletonMap("max_value", analyzerService.getMaxValue()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @GetMapping("/operation")
//    public ResponseEntity<Map<String, Double>> getAverageOrMedian(@RequestBody Map<String, String> operation) {
//        if (operation.get("operation").equals("average")) {
//            return new ResponseEntity<>(Collections.singletonMap("average", analyzerService.getAverage()), HttpStatus.OK);
//        } else if (operation.get("operation").equals("median")) {
//            return new ResponseEntity<>(Collections.singletonMap("median_value", analyzerService.getMedian()), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    @GetMapping("/operation")
//    public ResponseEntity<Map<String, List<List<Long>>>> getAscendingOrDescending(@RequestBody Map<String, String> operation) {
//        if (operation.get("operation").equals("max_ascending_sequences")) {
//            return new ResponseEntity<>(Collections.singletonMap("max_ascending_sequences", analyzerService.getMaxAscendingSequences()), HttpStatus.OK);
//        } else if (operation.get("operation").equals("max_descending_sequences")) {
//            return new ResponseEntity<>(Collections.singletonMap("max_descending_sequences", analyzerService.getMaxDescendingSequences()), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
}
