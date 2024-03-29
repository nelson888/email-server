package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.model.response.ErrorResponse
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest
import springfox.documentation.annotations.ApiIgnore

import javax.servlet.http.HttpServletRequest

@RestController
@ApiIgnore
class AppErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error"

    private final ErrorAttributes errorAttributes

    AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes
    }

    @RequestMapping(value = ERROR_PATH)
    ResponseEntity error(HttpServletRequest request) {
        Map<String, String> errorAttributes = getErrorAttributes(request)
        HttpStatus status = getStatus(request)
        return ResponseEntity.status(status)
                .body(new ErrorResponse(error: errorAttributes.get("error"),
                        message: errorAttributes.get("message"),
                        timestamp: errorAttributes.get("timestamp"),
                        path: errorAttributes.get("path")))
    }

    private Map<String, String> getErrorAttributes(HttpServletRequest request) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request)
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(servletWebRequest, false)
        Map<String, String> map = new HashMap<>()
        errorAttributes.forEach({ String key, Object value -> map.put(key, value.toString()) })
        return map
    }

    @Override
    String getErrorPath() {
        return ERROR_PATH
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code")
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode)
            }
            catch (Exception ex) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR
    }
}
