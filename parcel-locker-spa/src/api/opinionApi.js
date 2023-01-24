import {get, post, put, putWithBody, remove} from './api';

export function apiGetOpinions() {
    return get('/opinions')
}

export function getOpinion(id) {
    return get(`/opinions/${id}`)
}

export function postOpinion(body) {
    return post('/opinions',body)
}

export function putOpinion(body) {
    return putWithBody('/opinions', body)
}

export function putOpinionHidden(id) {
    return put(`/opinions/${id}`)
}

export function deleteOpinion(id) {
    return remove(`/opinions/${id}`)
}

export function getOpinionsForClient(username) {
    return get(`/opinions/client/${username}`)
}

export function getOpinionStarReviews() {
    return get('/opinions/starReviews')
}
