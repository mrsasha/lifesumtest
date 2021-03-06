package com.alterego.lifesumtest;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.alterego.lifesumtest.LifesumSearchDaoEntry;
import com.alterego.lifesumtest.LifesumItem;

import com.alterego.lifesumtest.LifesumSearchDaoEntryDao;
import com.alterego.lifesumtest.LifesumItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig lifesumSearchDaoEntryDaoConfig;
    private final DaoConfig lifesumItemDaoConfig;

    private final LifesumSearchDaoEntryDao lifesumSearchDaoEntryDao;
    private final LifesumItemDao lifesumItemDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        lifesumSearchDaoEntryDaoConfig = daoConfigMap.get(LifesumSearchDaoEntryDao.class).clone();
        lifesumSearchDaoEntryDaoConfig.initIdentityScope(type);

        lifesumItemDaoConfig = daoConfigMap.get(LifesumItemDao.class).clone();
        lifesumItemDaoConfig.initIdentityScope(type);

        lifesumSearchDaoEntryDao = new LifesumSearchDaoEntryDao(lifesumSearchDaoEntryDaoConfig, this);
        lifesumItemDao = new LifesumItemDao(lifesumItemDaoConfig, this);

        registerDao(LifesumSearchDaoEntry.class, lifesumSearchDaoEntryDao);
        registerDao(LifesumItem.class, lifesumItemDao);
    }
    
    public void clear() {
        lifesumSearchDaoEntryDaoConfig.getIdentityScope().clear();
        lifesumItemDaoConfig.getIdentityScope().clear();
    }

    public LifesumSearchDaoEntryDao getLifesumSearchDaoEntryDao() {
        return lifesumSearchDaoEntryDao;
    }

    public LifesumItemDao getLifesumItemDao() {
        return lifesumItemDao;
    }

}
