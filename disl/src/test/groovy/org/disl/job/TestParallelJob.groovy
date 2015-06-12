/*
 * Copyright 2015 Karel H�bl <karel.huebl@gmail.com>.
 *
 * This file is part of disl.
 *
 * Disl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Disl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Disl.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.disl.job

import org.disl.pattern.Executable
import org.junit.Test

class TestParallelJob {
	
	@Test
	public void testExecute() {
		new ParallelJob().addAll([new TestingJob(),
				new TestingJob(),
				new TestingJob(),
				new TestingJob(),
				new TestingJob(),
				new TestingExecutable(),
				new TestingExecutable()
			]).execute()
	}
	
	static class TestingJob extends ParallelJob {
		TestingJob() {
			addAll([new TestingExecutable()])
		}
	}

	static class TestingExecutable implements Executable {
		void execute() {
			Thread.sleep(500)
		}
		void simulate(){}
	}

}
