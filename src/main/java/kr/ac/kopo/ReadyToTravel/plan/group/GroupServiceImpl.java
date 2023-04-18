package kr.ac.kopo.ReadyToTravel.plan.group;

import kr.ac.kopo.ReadyToTravel.dto.GroupDTO;
import kr.ac.kopo.ReadyToTravel.entity.board.BoardEntity;
import kr.ac.kopo.ReadyToTravel.entity.group.GroupEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupServiceImpl implements GroupService {
    final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void createGroup(GroupDTO groupDTO) {
        GroupEntity entity = groupDTO.convertToEntity(groupDTO);

        groupRepository.save(entity);
    }

    @Override
    public void removeGroup(Long groupNum) {
        groupRepository.deleteById(groupNum);
    }

    @Override
    public void groupAddMember(Long GroupNum, Long memberNum) {

    }


}
