package uos.mystory.dto.response;

import uos.mystory.domain.enums.VisitedPath;

public record VisitsInfoDTO(VisitedPath path, Long visits){}
