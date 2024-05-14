package ru.alex0d.investbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.ai.openai.OpenAiChatClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.alex0d.investbackend.dto.TarotCard
import ru.alex0d.investbackend.dto.TarotPredictionResponse
import ru.alex0d.investbackend.dto.toRussianName

@RestController
@CrossOrigin
@RequestMapping("/api/tarot")
class TarotController(
    private val chatClient: OpenAiChatClient
) {

    @Operation(summary = "Get tarot prediction", description = "Get tarot prediction by stock name")
    @ApiResponse(responseCode = "200", description = "Prediction retrieved successfully")
    @GetMapping("/{stockName}")
    fun getPrediction(@Parameter(description = "Stock name") @PathVariable stockName: String): ResponseEntity<TarotPredictionResponse> {
        val card = TarotCard.entries.random()  // secret esoteric technology

        val response =
            chatClient.call("Тебе необходимо провести анализ акции по карте Таро. Твой ответ будет использован в соответствующем сервисе и не должен выглядеть как ответ чат-бота в диалоге. Ответ не должен содержать заголовков и различную предупреждающую информацию о необходимости провести другой анализ. При анализе наибольшее внимание удели котировкам. Придумай 3-4 абзаца. Карта: ${card.toRussianName()}")

        return ResponseEntity.ok(
            TarotPredictionResponse(
                cardName = card.name.lowercase(),
                prediction = response
            )
        )
    }
}