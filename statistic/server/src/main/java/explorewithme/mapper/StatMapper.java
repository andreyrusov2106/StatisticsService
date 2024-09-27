package explorewithme.mapper;


import explorewithme.model.Stats;
import ru.practicum.dtos.StatRequestDto;
import ru.practicum.dtos.StatResponseDto;

public class StatMapper {
    public static StatResponseDto toStatDto(Stats stats) {
        return StatResponseDto.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .build();
    }

    public static void toStat(Stats stat, StatRequestDto statDto) {
        if (statDto.getId() != null) stat.setId(statDto.getId());
        if (statDto.getApp() != null) stat.setApp(statDto.getApp());
        if (statDto.getUri() != null) stat.setUri(statDto.getUri());
        if (statDto.getIp() != null) stat.setIp(statDto.getIp());
        if (statDto.getTimeStamp() != null) stat.setTimeStamp(statDto.getTimeStamp());
    }
}
