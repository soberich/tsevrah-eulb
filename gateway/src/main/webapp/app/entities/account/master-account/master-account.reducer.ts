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
import { IMasterAccount, defaultValue } from 'app/shared/model/account/master-account.model';

export const ACTION_TYPES = {
  FETCH_MASTERACCOUNT_LIST: 'masterAccount/FETCH_MASTERACCOUNT_LIST',
  FETCH_MASTERACCOUNT: 'masterAccount/FETCH_MASTERACCOUNT',
  CREATE_MASTERACCOUNT: 'masterAccount/CREATE_MASTERACCOUNT',
  UPDATE_MASTERACCOUNT: 'masterAccount/UPDATE_MASTERACCOUNT',
  DELETE_MASTERACCOUNT: 'masterAccount/DELETE_MASTERACCOUNT',
  RESET: 'masterAccount/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMasterAccount>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MasterAccountState = Readonly<typeof initialState>;

// Reducer

export default (state: MasterAccountState = initialState, action): MasterAccountState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MASTERACCOUNT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MASTERACCOUNT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MASTERACCOUNT):
    case REQUEST(ACTION_TYPES.UPDATE_MASTERACCOUNT):
    case REQUEST(ACTION_TYPES.DELETE_MASTERACCOUNT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MASTERACCOUNT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MASTERACCOUNT):
    case FAILURE(ACTION_TYPES.CREATE_MASTERACCOUNT):
    case FAILURE(ACTION_TYPES.UPDATE_MASTERACCOUNT):
    case FAILURE(ACTION_TYPES.DELETE_MASTERACCOUNT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MASTERACCOUNT_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_MASTERACCOUNT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MASTERACCOUNT):
    case SUCCESS(ACTION_TYPES.UPDATE_MASTERACCOUNT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MASTERACCOUNT):
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

const apiUrl = 'services/account/api/master-accounts';

// Actions

export const getEntities: ICrudGetAllAction<IMasterAccount> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MASTERACCOUNT_LIST,
    payload: axios.get<IMasterAccount>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMasterAccount> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MASTERACCOUNT,
    payload: axios.get<IMasterAccount>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMasterAccount> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MASTERACCOUNT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IMasterAccount> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MASTERACCOUNT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMasterAccount> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MASTERACCOUNT,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
