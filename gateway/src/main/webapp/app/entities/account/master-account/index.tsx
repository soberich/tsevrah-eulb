import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MasterAccount from './master-account';
import MasterAccountDetail from './master-account-detail';
import MasterAccountUpdate from './master-account-update';
import MasterAccountDeleteDialog from './master-account-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MasterAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MasterAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MasterAccountDetail} />
      <ErrorBoundaryRoute path={match.url} component={MasterAccount} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MasterAccountDeleteDialog} />
  </>
);

export default Routes;
