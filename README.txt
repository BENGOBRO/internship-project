ПРИМЕРЫ REST-ЗАПРОСОВ И ОТВЕТОВ (JSON):

POST: http://localhost:8881/analyzer/read_file; {"file_path": "C:/Users/user/projects/InternRelexProject/src/main/resources/10m.txt"}
Response: 200 OK Status

GET: http://localhost:8881/analyzer/get_max_value
Response: {"max_value": 49999978}

GET: http://localhost:8881/analyzer/get_min_value
Response: {"min_value": -49999996}

GET: http://localhost:8881/analyzer/get_median
Response: {"median": 25216.0}

GET: http://localhost:8881/analyzer/get_average
Response:{"average": 7364.418442641844}

GET: http://localhost:8881/analyzer/get_max_ascending_sequences
Response: {
    "max_ascending_sequences": [
        [
            -48190694,
            -47725447,
            -43038241,
            -20190291,
            -17190728,
            -6172572,
            8475960,
            25205909,
            48332507,
            48676185
        ]
    ]
}

GET: http://localhost:8881/analyzer/get_max_descending_sequences
Response: {
    "max_descending_sequences": [
        [
            47689379,
            42381213,
            30043880,
            12102356,
            -4774057,
            -5157723,
            -11217378,
            -23005285,
            -23016933,
            -39209115,
            -49148762
        ]
    ]
}
