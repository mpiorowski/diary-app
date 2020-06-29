import {apiRequest} from "../ApiRequest";

export function serviceGetUser() {
  return apiRequest({
    url: "/api/auth/user",
    method: "GET"
  })
}

export function serviceLogIn(credentials) {
  return apiRequest({
    url: "/api/auth/log",
    method: "POST",
    body: JSON.stringify(credentials)
  });
}
