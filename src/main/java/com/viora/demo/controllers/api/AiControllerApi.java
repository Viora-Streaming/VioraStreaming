package com.viora.demo.controllers.api;

import com.viora.demo.dto.AiMessageRequestDTO;
import com.viora.demo.dto.AiMessageResponseDTO;
import com.viora.demo.dto.ErrorResponseDTO;
import com.viora.demo.dto.RecommendationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/ai")
@Tag(name = "AI", description = "AI consultant endpoints")
public interface AiControllerApi {

  @Operation(summary = "Send AI message")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = AiMessageResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Validation error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @PostMapping("/messages")
  ResponseEntity<AiMessageResponseDTO> message(@RequestBody AiMessageRequestDTO request);

  @Operation(summary = "Get AI recommendations")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = RecommendationResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @GetMapping("/recommendations")
  ResponseEntity<RecommendationResponseDTO> recommendations(@RequestParam(required = false) String genre);
}
