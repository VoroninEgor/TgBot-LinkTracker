package edu.java.controller.link;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Validated
@Tag(name = "links", description = "the links API")
public interface LinkApi {
    /**
     * DELETE /links : Убрать отслеживание ссылки
     *
     * @param tgChatId  (required)
     * @param removeLinkRequest  (required)
     * @return Ссылка успешно убрана (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     *         or Ссылка не найдена (status code 404)
     */
    @Operation(
        operationId = "linksDelete",
        summary = "Убрать отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {
                @Content(mediaType = "application/json")
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "",
        produces = { "application/json" },
        consumes = { "application/json" }
    )

    ResponseEntity<LinkResponse> linksDelete(
        @NotNull
        @Parameter(name = "Tg-Chat-Id", description = "", required = true, in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Parameter(name = "RemoveLinkRequest", description = "", required = true) @Valid @RequestBody
        RemoveLinkRequest removeLinkRequest
    );


    /**
     * GET /links : Получить все отслеживаемые ссылки
     *
     * @param tgChatId  (required)
     * @return Ссылки успешно получены (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     */
    @Operation(
        operationId = "linksGet",
        summary = "Получить все отслеживаемые ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json")
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "",
        produces = { "application/json" }
    )

    ResponseEntity<ListLinksResponse> linksGet(
        @NotNull @Parameter(name = "Tg-Chat-Id", description = "", required = true, in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId
    );


    /**
     * POST /links : Добавить отслеживание ссылки
     *
     * @param tgChatId  (required)
     * @param addLinkRequest  (required)
     * @return Ссылка успешно добавлена (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     */
    @Operation(
        operationId = "linksPost",
        summary = "Добавить отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json")
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "",
        produces = { "application/json" },
        consumes = { "application/json" }
    )

    ResponseEntity<LinkResponse> linksPost(
        @NotNull @Parameter(name = "Tg-Chat-Id", description = "", required = true, in = ParameterIn.HEADER)
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Parameter(name = "AddLinkRequest", description = "", required = true) @Valid @RequestBody
        AddLinkRequest addLinkRequest
    );

}
