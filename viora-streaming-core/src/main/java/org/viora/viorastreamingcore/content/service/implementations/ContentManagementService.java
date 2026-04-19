package org.viora.viorastreamingcore.content.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.content.dto.CastMember;
import org.viora.viorastreamingcore.content.dto.Content;
import org.viora.viorastreamingcore.content.dto.requests.CreateContentRequest;
import org.viora.viorastreamingcore.content.exception.CastMemberNotFoundException;
import org.viora.viorastreamingcore.content.exception.ContentAlreadyExistsException;
import org.viora.viorastreamingcore.content.exception.ContentNotFoundException;
import org.viora.viorastreamingcore.content.model.CastMemberModel;
import org.viora.viorastreamingcore.content.model.ContentModel;
import org.viora.viorastreamingcore.content.model.ContentToCastModel;
import org.viora.viorastreamingcore.content.repository.CastMemberRepository;
import org.viora.viorastreamingcore.content.repository.ContentRepository;
import org.viora.viorastreamingcore.content.service.use_cases.CreateContentUseCase;
import org.viora.viorastreamingcore.content.service.use_cases.GetContentUseCase;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentManagementService implements CreateContentUseCase, GetContentUseCase {

  private final CastMemberRepository castMemberRepository;
  private final ContentRepository contentRepository;

  @Override
  public Content createContent(CreateContentRequest request) {

    if(contentRepository.existsByTitle(request.title())){
      throw new ContentAlreadyExistsException(
          "Content with title " + request.title() + " already exists");
    }

    ContentModel contentModel = new ContentModel();
    contentModel.setTitle(request.title());
    contentModel.setDescription(request.description());
    contentModel.setSynopsis(request.synopsis());

    for(var cast : request.cast()){
      if(!castMemberRepository.existsById(cast.castMemberId())) {
        throw new CastMemberNotFoundException(
            "Cast member with id " + cast.castMemberId() + " was not found");
      }

      CastMemberModel member = castMemberRepository.findById(cast.castMemberId()).get();
      ContentToCastModel contentToCast = new ContentToCastModel(null,
          contentModel, member, cast.roleName());

      contentModel.getCast().add(contentToCast);
    }

    contentModel = contentRepository.save(contentModel);

    return mapToContent(contentModel);
  }

  @Override
  public Content findContentByTitle(String title) {
    if(!contentRepository.existsByTitle(title)){
      throw new ContentNotFoundException(
          "Content with title " + title + " was not found"
      );
    }

    return mapToContent(contentRepository.getByTitle(title).get());
  }

  private Content mapToContent(ContentModel model){
    return new Content(
        model.getId(),
        model.getTitle(),
        model.getDescription(),
        model.getSynopsis(),
        mapToCastPerContent(model.getCast())
    );
  }

  private List<CastMember> mapToCastPerContent(List<ContentToCastModel> list){
    List<CastMember> result = new ArrayList<>();

    for(var obj : list){
      CastMemberModel model = obj.getCastMember();

      result.add(new CastMember(model.getId(), model.getName(), obj.getRoleName()));
    }

    return result;
  }
}
