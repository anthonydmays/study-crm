package com.codedifferently.studycrm.contacts.api.web;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetContactsResponse {

  private List<GetContactResponse> contacts;
}
