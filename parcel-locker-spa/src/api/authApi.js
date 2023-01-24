import { get, post, postNoResponse, put, putWithBody } from './api';

export async function postLogin(body) {
  return post('/auth/login', body);
}

export async function apiLogOut() {
  return postNoResponse('/auth/logout');
}

export async function apiGetSelf() {
  return get('/clients/self');
}

export async function apiGetActiveClients() {
  return get('/clients/active-clients');
}

export async function apiGetArchivedClients() {
  return get('/clients/archived-clients');
}

export async function apiArchiveClient(username) {
  return put(`/clients/${username}/archive`);
}

export async function apiActiveClient(username) {
  return put(`/clients/${username}/active`);
}

export async function apiChangePassword(body) {
  return putWithBody('/clients/self', body);
}

