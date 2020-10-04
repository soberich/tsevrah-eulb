import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AccountApplication from './account-application';
import AccountApplicationDetail from './account-application-detail';
import AccountApplicationUpdate from './account-application-update';
import AccountApplicationDeleteDialog from './account-application-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AccountApplicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AccountApplicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AccountApplicationDetail} />
      <ErrorBoundaryRoute path={match.url} component={AccountApplication} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AccountApplicationDeleteDialog} />
  </>
);

export default Routes;
