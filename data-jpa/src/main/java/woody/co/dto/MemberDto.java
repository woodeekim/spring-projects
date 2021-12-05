package woody.co.dto;

import lombok.Data;
import woody.co.entity.Member;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.teamName = name;
    }

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        if (member.getTeam().getName() != null) {
            this.teamName = member.getTeam().getName();
        }
    }
}
