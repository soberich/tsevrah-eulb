import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MonetaryTransaction from './monetary-transaction';
import MonetaryTransactionDetail from './monetary-transaction-detail';
import MonetaryTransactionUpdate from './monetary-transaction-update';
import MonetaryTransactionDeleteDialog from './monetary-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MonetaryTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MonetaryTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MonetaryTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={MonetaryTransaction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MonetaryTransactionDeleteDialog} />
  </>
);

export default Routes;
