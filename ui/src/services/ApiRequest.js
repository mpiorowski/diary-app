import {ACCESS_TOKEN} from "../config/AuthConfig";

export const apiRequest = (options) => {

  const headers = new Headers({
    'Content-Type': 'application/json'
  });

  if (localStorage.getItem(ACCESS_TOKEN)) {
    headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN));
  }

  const defaults = {headers: headers};
  options = Object.assign({}, defaults, options);

  return fetch(options.url, options).then(response => {
    const contentType = response.headers.get("content-type");
    if (response.ok) {
      // return response.json();
      if (contentType && contentType.indexOf("application/json") !== -1) {
        return response.json();
      } else {
        return true;
      }
    } else {
      console.log('error');
      return Promise.reject(response);
    }
  });
};
