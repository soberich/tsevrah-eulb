import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AccountState from './account-state';
import AccountStateDetail from './account-state-detail';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AccountStateDetail} />
      <ErrorBoundaryRoute path={match.url} component={AccountState} />
    </Switch>
  </>
);

export default Routes;
