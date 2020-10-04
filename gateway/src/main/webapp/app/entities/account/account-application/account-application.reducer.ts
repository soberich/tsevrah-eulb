import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IAccountApplication, defaultValue } from 'app/shared/model/account/account-application.model';

export const ACTION_TYPES = {
  FETCH_ACCOUNTAPPLICATION_LIST: 'accountApplication/FETCH_ACCOUNTAPPLICATION_LIST',
  FETCH_ACCOUNTAPPLICATION: 'accountApplication/FETCH_ACCOUNTAPPLICATION',
  CREATE_ACCOUNTAPPLICATION: 'accountApplication/CREATE_ACCOUNTAPPLICATION',
  UPDATE_ACCOUNTAPPLICATION: 'accountApplication/UPDATE_ACCOUNTAPPLICATION',
  DELETE_ACCOUNTAPPLICATION: 'accountApplication/DELETE_ACCOUNTAPPLICATION',
  RESET: 'accountApplication/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAccountApplication>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AccountApplicationState = Readonly<typeof initialState>;

// Reducer

export default (state: AccountApplicationState = initialState, action): AccountApplicationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTAPPLICATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTAPPLICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ACCOUNTAPPLICATION):
    case REQUEST(ACTION_TYPES.UPDATE_ACCOUNTAPPLICATION):
    case REQUEST(ACTION_TYPES.DELETE_ACCOUNTAPPLICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTAPPLICATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTAPPLICATION):
    case FAILURE(ACTION_TYPES.CREATE_ACCOUNTAPPLICATION):
    case FAILURE(ACTION_TYPES.UPDATE_ACCOUNTAPPLICATION):
    case FAILURE(ACTION_TYPES.DELETE_ACCOUNTAPPLICATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTAPPLICATION_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTAPPLICATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACCOUNTAPPLICATION):
    case SUCCESS(ACTION_TYPES.UPDATE_ACCOUNTAPPLICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACCOUNTAPPLICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'services/account/api/account-applications';

// Actions

export const getEntities: ICrudGetAllAction<IAccountApplication> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ACCOUNTAPPLICATION_LIST,
    payload: axios.get<IAccountApplication>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAccountApplication> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACCOUNTAPPLICATION,
    payload: axios.get<IAccountApplication>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAccountApplication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACCOUNTAPPLICATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IAccountApplication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACCOUNTAPPLICATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAccountApplication> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACCOUNTAPPLICATION,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
