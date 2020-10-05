import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAccountState } from 'app/shared/model/transaction/account-state.model';
import { getEntities as getAccountStates } from 'app/entities/transaction/account-state/account-state.reducer';
import { getEntity, updateEntity, createEntity, reset } from './monetary-transaction.reducer';
import { IMonetaryTransaction } from 'app/shared/model/transaction/monetary-transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMonetaryTransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MonetaryTransactionUpdate = (props: IMonetaryTransactionUpdateProps) => {
  const [accountStateID, setaccountStateID] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { monetaryTransactionEntity, accountStates, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/monetary-transaction');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getAccountStates();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...monetaryTransactionEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayApp.transactionMonetaryTransaction.home.createOrEditLabel">
            <Translate contentKey="gatewayApp.transactionMonetaryTransaction.home.createOrEditLabel">
              Create or edit a MonetaryTransaction
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : monetaryTransactionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="monetary-transaction-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="monetary-transaction-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="recipientIDLabel" for="monetary-transaction-recipientID">
                  <Translate contentKey="gatewayApp.transactionMonetaryTransaction.recipientID">Recipient ID</Translate>
                </Label>
                <AvField
                  id="monetary-transaction-recipientID"
                  type="string"
                  className="form-control"
                  name="recipientID"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="senderIDLabel" for="monetary-transaction-senderID">
                  <Translate contentKey="gatewayApp.transactionMonetaryTransaction.senderID">Sender ID</Translate>
                </Label>
                <AvField
                  id="monetary-transaction-senderID"
                  type="string"
                  className="form-control"
                  name="senderID"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="monetary-transaction-amount">
                  <Translate contentKey="gatewayApp.transactionMonetaryTransaction.amount">Amount</Translate>
                </Label>
                <AvField
                  id="monetary-transaction-amount"
                  type="text"
                  name="amount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="monetary-transaction-account">
                  <Translate contentKey="gatewayApp.transactionMonetaryTransaction.account">Account</Translate>
                </Label>
                <AvInput id="monetary-transaction-account" type="select" className="form-control" name="accountStateID" required>
                  {accountStates
                    ? accountStates.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.customerID}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/monetary-transaction" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  accountStates: storeState.accountState.entities,
  monetaryTransactionEntity: storeState.monetaryTransaction.entity,
  loading: storeState.monetaryTransaction.loading,
  updating: storeState.monetaryTransaction.updating,
  updateSuccess: storeState.monetaryTransaction.updateSuccess,
});

const mapDispatchToProps = {
  getAccountStates,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MonetaryTransactionUpdate);
