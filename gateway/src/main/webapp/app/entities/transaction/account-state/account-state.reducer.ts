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
import { IAccountState, defaultValue } from 'app/shared/model/transaction/account-state.model';

export const ACTION_TYPES = {
  FETCH_ACCOUNTSTATE_LIST: 'accountState/FETCH_ACCOUNTSTATE_LIST',
  FETCH_ACCOUNTSTATE: 'accountState/FETCH_ACCOUNTSTATE',
  RESET: 'accountState/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAccountState>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AccountStateState = Readonly<typeof initialState>;

// Reducer

export default (state: AccountStateState = initialState, action): AccountStateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTSTATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTSTATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTSTATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTSTATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTSTATE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTSTATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'services/transaction/api/account-states';

// Actions

export const getEntities: ICrudGetAllAction<IAccountState> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ACCOUNTSTATE_LIST,
    payload: axios.get<IAccountState>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAccountState> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACCOUNTSTATE,
    payload: axios.get<IAccountState>(requestUrl),
  };
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
