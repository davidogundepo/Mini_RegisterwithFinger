// Part of SourceAFIS: https://sourceafis.machinezoo.com
package com.testing.aramis.sourceafis;

import java.nio.*;
import java.util.*;

class SkeletonMinutia {
	final Cell position;
	final List<SkeletonRidge> ridges = new ArrayList<>();
	SkeletonMinutia(Cell position) {
		this.position = position;
	}
	void attachStart(SkeletonRidge ridge) {
		if (!ridges.contains(ridge)) {
			ridges.add(ridge);
			ridge.start(this);
		}
	}
	void detachStart(SkeletonRidge ridge) {
		if (ridges.contains(ridge)) {
			ridges.remove(ridge);
			if (ridge.start() == this)
				ridge.start(null);
		}
	}
	void write(ByteBuffer buffer) {
		for (SkeletonRidge ridge : ridges)
			if (ridge.points instanceof CircularList)
				ridge.write(buffer);
	}
	int serializedSize() {
		return ridges.stream().filter(r -> r.points instanceof CircularList).mapToInt(r -> r.serializedSize()).sum();
	}
	@Override public String toString() {
		return String.format("%s*%d", position.toString(), ridges.size());
	}
}
