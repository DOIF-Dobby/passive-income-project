package org.mj.passiveincome.system.data

import org.mj.passiveincome.system.core.base.BaseException
import org.mj.passiveincome.system.core.base.BaseStatus

class EntityNotFoundException : BaseException(status = BaseStatus.FAIL, message = "Entity Not Found")
